import { Router } from 'express';
import fruit from '../controller/fruit';
import User from '../controller/user';
const router = Router();
router.route('/fruit').post(fruit.addFruit)
router.route('/fruit/:id').get([User.verifyToken, fruit.getAllFruit])
router.route('/fruit/getAllFruitByPage/:id').get([User.verifyToken,fruit.getAllFruitByPage])
router.route('/fruit/getAllFruitQueryMap/:id').get([User.verifyToken,fruit.getAllFruitQueryMap])
export default router;