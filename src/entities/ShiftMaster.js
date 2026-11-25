/**
 * ShiftTimingDetail Entity
 * Represents child rows for individual timing slots within a Shift Master.
 */
export class ShiftTimingDetail {
  constructor({
    id,
    shiftMasterId = null,
    slotNumber = 1,
    inTime = '08:00',
    outTime = '16:30',
    isNextDay = false,
    breakDuration = 0,
    workingHours = 0
  } = {}) {
    this.id = id || `std-${Date.now()}-${Math.random().toString(36).substr(2, 5)}`;
    this.shiftMasterId = shiftMasterId;
    this.slotNumber = Number(slotNumber) || 1;
    this.inTime = inTime;
    this.outTime = outTime;
    this.isNextDay = Boolean(isNextDay);
    this.breakDuration = Number(breakDuration) || 0; // minutes
    this.workingHours = Number(workingHours) || 0; // decimal hours
  }

  /**
   * Calculates slot working hours taking overnight +1 day and break duration into account
   */
  calculateWorkingHours() {
    if (!this.inTime || !this.outTime) {
      this.workingHours = 0;
      return 0;
    }

    const [inH, inM] = this.inTime.split(':').map(Number);
    const [outH, outM] = this.outTime.split(':').map(Number);

    const inTotalMinutes = inH * 60 + inM;
    let outTotalMinutes = outH * 60 + outM;

    if (this.isNextDay || outTotalMinutes < inTotalMinutes) {
      outTotalMinutes += 24 * 60;
    }

    const grossMinutes = Math.max(0, outTotalMinutes - inTotalMinutes);
    const netMinutes = Math.max(0, grossMinutes - (this.breakDuration || 0));
    this.workingHours = Number((netMinutes / 60).toFixed(2));
    return this.workingHours;
  }
}

/**
 * ShiftMaster Entity
 * Represents Master shift records in Composite Textile ERP.
 */
export class ShiftMaster {
  constructor({
    id,
    shiftCode,
    shiftName,
    shiftType = 'Fixed', // 'Fixed' | 'Rotation'
    description = '',
    activeFlag = true,
    timingDetails = [],
    totalWorkingHours = 0,
    createdAt = new Date().toISOString(),
    updatedAt = new Date().toISOString()
  } = {}) {
    this.id = id || `shift-${Date.now()}-${Math.random().toString(36).substr(2, 5)}`;
    this.shiftCode = (shiftCode || '').trim().toUpperCase();
    this.shiftName = (shiftName || '').trim();
    this.shiftType = shiftType === 'Rotation' ? 'Rotation' : 'Fixed';
    this.description = (description || '').trim();
    this.activeFlag = activeFlag !== false;

    this.timingDetails = Array.isArray(timingDetails)
      ? timingDetails.map((slot, index) => {
          const detail = new ShiftTimingDetail({
            ...slot,
            shiftMasterId: this.id,
            slotNumber: slot.slotNumber || index + 1
          });
          detail.calculateWorkingHours();
          return detail;
        })
      : [];

    this.totalWorkingHours = this.calculateTotalWorkingHours();
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  calculateTotalWorkingHours() {
    const total = this.timingDetails.reduce((sum, slot) => {
      const slotHours = slot.calculateWorkingHours ? slot.calculateWorkingHours() : (slot.workingHours || 0);
      return sum + Number(slotHours);
    }, 0);
    this.totalWorkingHours = Number(total.toFixed(2));
    return this.totalWorkingHours;
  }
}
