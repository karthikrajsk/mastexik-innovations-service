import { Router } from 'express';
import { ShiftMasterController } from '../controllers/ShiftMasterController.js';

const router = Router();

// Routes for Shift Master API
router.get('/', ShiftMasterController.getAll);
router.get('/:id', ShiftMasterController.getById);
router.post('/', ShiftMasterController.create);
router.put('/:id', ShiftMasterController.update);
router.delete('/:id', ShiftMasterController.delete);
router.patch('/:id/status', ShiftMasterController.toggleStatus);
router.put('/:id/status', ShiftMasterController.toggleStatus);

export default router;
