import { formatResponseError, formatResponseSuccess, formatResponseSuccessNoData } from '../config';
import Fruit from '../models/fruit';
const multer = require('multer');
const fs = require('fs');
const util = require('util');
const path = require('path');

const unlinkFile = util.promisify(fs.unlink);

const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'uploads/');
    },
    filename: function (req, file, cb) {
        const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1e9);
        cb(null, file.fieldname + '-' + uniqueSuffix + path.extname(file.originalname));
    },
});

const fileFilter = (req, file, cb) => {
    if (file.mimetype.startsWith('image/')) {
        cb(null, true);
    } else {
        cb(new Error('Only images are allowed!'), false);
    }
};

const upload = multer({ storage, fileFilter });
class FruitClass {
    async addFruit(req, res) {
        try {
            upload.array('images', 5)(req, res, async function (err) {
                if (err instanceof multer.MulterError) {
                    return res.status(400).json({ message: 'Error uploading images', error: err });
                } else if (err) {
                    return res.status(400).json({ message: 'Error uploading images', error: err });
                }
                const { name, quantity, price, description, idUser, idCompany } = req.body;
                const images = req.files.map(file => file.filename);
                const newFruit = new Fruit({
                    name,
                    quantity,
                    price,
                    description,
                    idUser,
                    idCompany,
                    images
                });
                const result = await newFruit.save();
                if (result) {
                    res.status(200).json(formatResponseSuccess(result, true, 'Thêm thành công'));
                }
            });
        } catch (error) {
            console.error('addFruit error:', error);
            res.status(500).json({ message: 'Internal server error', error });
        }
    }

    async getAllFruit(req, res) {
        try {
            const fruits = await Fruit.find({ idUser: req.params.id });
            res.status(200).json(fruits);
        } catch (error) {
            console.error('getAllFruit error:', error);
            return res.status(500).json(formatResponseError({ code: '500' }, false, 'Lỗi xảy ra trong quá trình thực thi'));
        }
    }

    async getAllFruitByPage(req, res) {
        try {
            const page = req.query.page || 1;
            const limit = req.query.limit || 10;
            const idUser = req.params.id;
            const skip = (page - 1) * limit;
            const fruits = await Fruit.find({ idUser })
                .skip(skip)
                .limit(parseInt(limit));

            res.status(200).json(fruits);
        } catch (error) {
            console.error('getAllFruit error:', error);
            return res.status(500).json(formatResponseError({ code: '500' }, false, 'Lỗi xảy ra trong quá trình thực thi'));
        }
    }

    async  getAllFruitQueryMap(req, res) {
        try {
            let query = { idUser: req.params.id }; 
    
            if (req.query.idCompany) {
                query.idCompany = req.query.idCompany;
            }
    
            if (req.query.price) {
                query.price = req.query.price;
            }
    
            if (req.query.name) {
                query.name = req.query.name;
            }
    
            const fruits = await Fruit.find(query);
            
            res.status(200).json(fruits);
        } catch (error) {
            console.error('getAllFruit error:', error);
            return res.status(500).json(formatResponseError({ code: '500' }, false, 'Lỗi xảy ra trong quá trình thực thi'));
        }
    }
}

export default new FruitClass();