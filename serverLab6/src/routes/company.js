import { Router } from 'express';
import company from '../controller/company';
const router = Router();
router.route('/company').post(company.addCompany).get(company.getAll)
router.route('/companySearch').get(company.searchByName)
router.route('/company/:id').delete(company.deleteOne)
export default router;