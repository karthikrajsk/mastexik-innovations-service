import { ShiftMaster } from '../entities/ShiftMaster.js';
import { CreateShiftMasterDTO, UpdateShiftMasterDTO } from '../dtos/ShiftMasterDTO.js';

export class ShiftMasterService {
  constructor() {
    this.shifts = [
      new ShiftMaster({
        id: 1,
        shiftCode: 'SH-MORN-A',
        shiftName: 'Morning Weaving Shift A',
        shiftType: 'Rotation',
        description: 'Primary morning production shift for Looms 01 - 24',
        activeFlag: true,
        timingDetails: [
          {
            id: 101,
            slotNumber: 1,
            inTime: '06:00',
            outTime: '14:00',
            isNextDay: false,
            breakDuration: 30
          }
        ]
      }),
      new ShiftMaster({
        id: 2,
        shiftCode: 'SH-AFTN-B',
        shiftName: 'Afternoon Weaving Shift B',
        shiftType: 'Rotation',
        description: 'Afternoon production and beam inspection shift',
        activeFlag: true,
        timingDetails: [
          {
            id: 102,
            slotNumber: 1,
            inTime: '14:00',
            outTime: '22:00',
            isNextDay: false,
            breakDuration: 30
          }
        ]
      }),
      new ShiftMaster({
        id: 3,
        shiftCode: 'SH-NGHT-C',
        shiftName: 'Night Weaving Shift C',
        shiftType: 'Rotation',
        description: 'Overnight continuous spinning and weaving operation',
        activeFlag: true,
        timingDetails: [
          {
            id: 103,
            slotNumber: 1,
            inTime: '22:00',
            outTime: '06:00',
            isNextDay: true,
            breakDuration: 30
          }
        ]
      }),
      new ShiftMaster({
        id: 4,
        shiftCode: 'GEN-ADMIN',
        shiftName: 'General Administrative & QC Shift',
        shiftType: 'Fixed',
        description: 'Standard office, quality lab, and warehouse maintenance hours',
        activeFlag: true,
        timingDetails: [
          {
            id: 104,
            slotNumber: 1,
            inTime: '09:00',
            outTime: '17:30',
            isNextDay: false,
            breakDuration: 60
          }
        ]
      }),
      new ShiftMaster({
        id: 5,
        shiftCode: 'SPLIT-MAINT',
        shiftName: 'Split Maintenance Shift',
        shiftType: 'Fixed',
        description: 'Two-slot schedule for loom mechanical servicing and gaiting setup',
        activeFlag: false,
        timingDetails: [
          {
            id: 105,
            slotNumber: 1,
            inTime: '08:00',
            outTime: '12:00',
            isNextDay: false,
            breakDuration: 0
          },
          {
            id: 106,
            slotNumber: 2,
            inTime: '15:00',
            outTime: '19:00',
            isNextDay: false,
            breakDuration: 0
          }
        ]
      })
    ];
  }

  getAllShifts({ search = '', shiftType = '', activeFlag, limit = 10000, offset = 0 } = {}) {
    let result = [...this.shifts];

    if (search) {
      const q = search.toLowerCase().trim();
      result = result.filter(s =>
        s.shiftCode.toLowerCase().includes(q) ||
        s.shiftName.toLowerCase().includes(q) ||
        (s.description && s.description.toLowerCase().includes(q))
      );
    }

    if (shiftType && shiftType !== 'All') {
      result = result.filter(s => s.shiftType === shiftType);
    }

    if (activeFlag !== undefined && activeFlag !== '' && activeFlag !== 'All') {
      const boolFlag = activeFlag === true || activeFlag === 'true';
      result = result.filter(s => s.activeFlag === boolFlag);
    }

    const total = result.length;
    const paginated = result.slice(Number(offset), Number(offset) + Number(limit));

    return {
      total,
      limit: Number(limit),
      offset: Number(offset),
      data: paginated
    };
  }

  getShiftById(id) {
    return this.shifts.find(s => s.id.toString() === id.toString()) || null;
  }

  getShiftByCode(code) {
    if (!code) return null;
    return this.shifts.find(s => s.shiftCode.toUpperCase() === code.trim().toUpperCase()) || null;
  }

  createShift(payload) {
    const validation = CreateShiftMasterDTO.validate(payload);
    if (!validation.isValid) {
      const error = new Error(validation.errors.join(', '));
      error.statusCode = 400;
      throw error;
    }

    const { shiftCode, shiftName, shiftType, description, activeFlag, timingDetails } = validation.data;

    const existing = this.getShiftByCode(shiftCode);
    if (existing) {
      const error = new Error(`Shift Code "${shiftCode}" already exists.`);
      error.statusCode = 409;
      throw error;
    }

    const newShift = new ShiftMaster({
      id: Date.now(),
      shiftCode,
      shiftName,
      shiftType,
      description,
      activeFlag,
      timingDetails
    });

    this.shifts.unshift(newShift);
    return newShift;
  }

  updateShift(id, payload) {
    const existingIndex = this.shifts.findIndex(s => s.id.toString() === id.toString());
    if (existingIndex === -1) {
      const error = new Error(`Shift with ID "${id}" not found.`);
      error.statusCode = 404;
      throw error;
    }

    const validation = UpdateShiftMasterDTO.validate(payload);
    if (!validation.isValid) {
      const error = new Error(validation.errors.join(', '));
      error.statusCode = 400;
      throw error;
    }

    const { shiftCode, shiftName, shiftType, description, activeFlag, timingDetails } = validation.data;

    const codeConflict = this.shifts.find(
      s => s.id.toString() !== id.toString() && s.shiftCode.toUpperCase() === shiftCode.toUpperCase()
    );
    if (codeConflict) {
      const error = new Error(`Shift Code "${shiftCode}" is already in use by another shift.`);
      error.statusCode = 409;
      throw error;
    }

    const updatedShift = new ShiftMaster({
      id: this.shifts[existingIndex].id,
      shiftCode,
      shiftName,
      shiftType,
      description,
      activeFlag,
      timingDetails,
      createdAt: this.shifts[existingIndex].createdAt,
      updatedAt: new Date().toISOString()
    });

    this.shifts[existingIndex] = updatedShift;
    return updatedShift;
  }

  deleteShift(id) {
    const existingIndex = this.shifts.findIndex(s => s.id.toString() === id.toString());
    if (existingIndex === -1) {
      const error = new Error(`Shift with ID "${id}" not found.`);
      error.statusCode = 404;
      throw error;
    }

    const deleted = this.shifts.splice(existingIndex, 1)[0];
    return deleted;
  }

  toggleStatus(id, activeFlag) {
    const shift = this.getShiftById(id);
    if (!shift) {
      const error = new Error(`Shift with ID "${id}" not found.`);
      error.statusCode = 404;
      throw error;
    }

    shift.activeFlag = activeFlag !== undefined ? Boolean(activeFlag) : !shift.activeFlag;
    shift.updatedAt = new Date().toISOString();
    return shift;
  }
}

export const shiftMasterService = new ShiftMasterService();
