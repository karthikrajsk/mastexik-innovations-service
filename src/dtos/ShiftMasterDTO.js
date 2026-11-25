/**
 * Data Transfer Objects (DTO) and Validation for Shift Master
 */

export class ShiftTimingDetailDTO {
  static validate(data, index = 0) {
    const errors = [];
    const timePattern = /^([01]\d|2[0-3]):([0-5]\d)$/;

    if (!data.inTime || !timePattern.test(data.inTime)) {
      errors.push(`Row ${index + 1}: Shift In Time must be valid 24-hr format (HH:mm)`);
    }

    if (!data.outTime || !timePattern.test(data.outTime)) {
      errors.push(`Row ${index + 1}: Shift Out Time must be valid 24-hr format (HH:mm)`);
    }

    const breakDuration = Number(data.breakDuration);
    if (isNaN(breakDuration) || breakDuration < 0) {
      errors.push(`Row ${index + 1}: Break Duration must be a non-negative number`);
    }

    return {
      isValid: errors.length === 0,
      errors,
      sanitized: {
        id: data.id,
        slotNumber: Number(data.slotNumber) || index + 1,
        inTime: data.inTime,
        outTime: data.outTime,
        isNextDay: Boolean(data.isNextDay),
        breakDuration: Math.max(0, Number(data.breakDuration) || 0)
      }
    };
  }
}

export class CreateShiftMasterDTO {
  static validate(payload = {}) {
    const errors = [];

    const shiftCode = (payload.shiftCode || '').toString().trim().toUpperCase();
    if (!shiftCode) {
      errors.push('Shift Code is required.');
    } else if (shiftCode.length < 2 || shiftCode.length > 20) {
      errors.push('Shift Code must be between 2 and 20 characters.');
    }

    const shiftName = (payload.shiftName || '').toString().trim();
    if (!shiftName) {
      errors.push('Shift Name is required.');
    } else if (shiftName.length < 3 || shiftName.length > 100) {
      errors.push('Shift Name must be between 3 and 100 characters.');
    }

    const allowedTypes = ['Fixed', 'Rotation'];
    const shiftType = allowedTypes.includes(payload.shiftType) ? payload.shiftType : 'Fixed';

    const description = (payload.description || '').toString().trim();

    const activeFlag = payload.activeFlag !== undefined ? Boolean(payload.activeFlag) : true;

    if (!Array.isArray(payload.timingDetails) || payload.timingDetails.length === 0) {
      errors.push('At least one Shift Timing row is required.');
    }

    const sanitizedTimings = [];
    if (Array.isArray(payload.timingDetails)) {
      payload.timingDetails.forEach((slot, idx) => {
        const slotValidation = ShiftTimingDetailDTO.validate(slot, idx);
        if (!slotValidation.isValid) {
          errors.push(...slotValidation.errors);
        } else {
          sanitizedTimings.push(slotValidation.sanitized);
        }
      });
    }

    return {
      isValid: errors.length === 0,
      errors,
      data: {
        shiftCode,
        shiftName,
        shiftType,
        description,
        activeFlag,
        timingDetails: sanitizedTimings
      }
    };
  }
}

export class UpdateShiftMasterDTO {
  static validate(payload = {}) {
    return CreateShiftMasterDTO.validate(payload);
  }
}
