import { shiftMasterService } from '../services/ShiftMasterService.js';

export const ShiftMasterController = {
  getAll: (req, res) => {
    try {
      const { search, shiftType, activeFlag, limit, offset, fetchAll } = req.query;
      const effectiveLimit = fetchAll === 'true' ? 10000 : limit;
      const effectiveOffset = fetchAll === 'true' ? 0 : offset;

      const result = shiftMasterService.getAllShifts({
        search,
        shiftType,
        activeFlag,
        limit: effectiveLimit,
        offset: effectiveOffset
      });

      return res.json({
        success: true,
        data: result
      });
    } catch (err) {
      return res.status(500).json({ success: false, error: err.message });
    }
  },

  getById: (req, res) => {
    try {
      const shift = shiftMasterService.getShiftById(req.params.id);
      if (!shift) {
        return res.status(404).json({ success: false, error: `Shift #${req.params.id} not found.` });
      }
      return res.json({ success: true, data: shift });
    } catch (err) {
      return res.status(500).json({ success: false, error: err.message });
    }
  },

  create: (req, res) => {
    try {
      const created = shiftMasterService.createShift(req.body);
      return res.status(201).json({
        success: true,
        message: `Shift Master "${created.shiftCode}" created successfully.`,
        data: created
      });
    } catch (err) {
      return res.status(err.statusCode || 500).json({ success: false, error: err.message });
    }
  },

  update: (req, res) => {
    try {
      const updated = shiftMasterService.updateShift(req.params.id, req.body);
      return res.json({
        success: true,
        message: `Shift Master "${updated.shiftCode}" updated successfully.`,
        data: updated
      });
    } catch (err) {
      return res.status(err.statusCode || 500).json({ success: false, error: err.message });
    }
  },

  delete: (req, res) => {
    try {
      const deleted = shiftMasterService.deleteShift(req.params.id);
      return res.json({
        success: true,
        message: `Shift Master "${deleted.shiftCode}" deleted successfully.`,
        data: deleted
      });
    } catch (err) {
      return res.status(err.statusCode || 500).json({ success: false, error: err.message });
    }
  },

  toggleStatus: (req, res) => {
    try {
      const { activeFlag } = req.body;
      const updated = shiftMasterService.toggleStatus(req.params.id, activeFlag);
      return res.json({
        success: true,
        message: `Shift status updated to ${updated.activeFlag ? 'Active' : 'Inactive'}.`,
        data: updated
      });
    } catch (err) {
      return res.status(err.statusCode || 500).json({ success: false, error: err.message });
    }
  }
};
