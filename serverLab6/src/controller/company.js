import Company from "../models/company";
import { formatResponseError, formatResponseSuccess, formatResponseSuccessNoData } from '../config';
class CompanyClass {
    async addCompany(req, res) {
        try {
            const { name } = req.body;
            const newCompany = new Company({
                name
            });
            const result = await newCompany.save();
            if (result) {
                res.status(200).json(formatResponseSuccess(result, true, 'Thêm thành công'));
            }
        } catch (error) {
            console.error('addCompany error:', error);
            return res.status(500).json(formatResponseError({ code: '500' }, false, 'Lỗi xảy ra trong quá trình thêm công ty'));
        }
    }

    async deleteOne(req, res) {
        try {
            const { id } = req.params;
            const result = await Company.deleteOne({ _id: id });
            if (result.deletedCount > 0) {
                res.status(200).json(formatResponseSuccess(null, true, 'Xóa thành công'));
            } else {
                res.status(404).json(formatResponseError({ code: '404' }, false, 'Không tìm thấy công ty để xóa'));
            }
        } catch (error) {
            console.error('deleteOne error:', error);
            return res.status(500).json(formatResponseError({ code: '500' }, false, 'Lỗi xảy ra trong quá trình xóa công ty'));
        }
    }

    async getAll(req, res) {
        try {
            const companies = await Company.find();
            res.status(200).json(companies);
        } catch (error) {
            console.error('getAll error:', error);
            return res.status(500).json(formatResponseError({ code: '500' }, false, 'Lỗi xảy ra trong quá trình lấy danh sách công ty'));
        }
    }

    async searchByName(req, res) {
        try {
            console.error('searchByName error:');
            const { name } = req.query;
            const companies = await Company.find({ name: { $regex: name, $options: 'i' } });
            res.status(200).json(companies);
        } catch (error) {
            console.error('searchByName error:', error);
            return res.status(500).json(formatResponseError({ code: '500' }, false, 'Lỗi xảy ra trong quá trình tìm kiếm công ty'));
        }
    }
}
export default new CompanyClass();