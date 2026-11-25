import express from 'express';
import cors from 'cors';
import shiftRoutes from './routes/shiftRoutes.js';

const app = express();
const PORT = process.env.PORT || 8011;

app.use(cors());
app.use(express.json());

// API Request Logger
app.use((req, res, next) => {
  console.log(`[Composite ERP Server] ${req.method} ${req.url}`);
  next();
});

// Health check endpoint
app.get('/api/health', (req, res) => {
  res.json({ status: 'UP', message: 'Composite ERP Server is running' });
});

// Shift Master REST API Routes (supports all variations: /api/shift, /api/shifts, /shift, /shifts, /api/shift-master)
app.use([
  '/api/shifts',
  '/api/shift',
  '/shifts',
  '/shift',
  '/api/shift-master',
  '/shift-master'
], shiftRoutes);

app.listen(PORT, () => {
  console.log(`🚀 Composite ERP Server running on http://localhost:${PORT}`);
  console.log(`⏱️ Shift Master API available at http://localhost:${PORT}/api/shifts and /api/shift`);
});

export default app;
