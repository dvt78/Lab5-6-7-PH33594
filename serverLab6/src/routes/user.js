import { Router } from 'express';
import user from '../controller/user';
const router = Router();
router.route('/auth/register').post(user.register)
router.route('/auth/login').post(user.login)

export default router;