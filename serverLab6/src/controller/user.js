import { formatResponseError, formatResponseSuccess, formatResponseSuccessNoData } from '../config';
import User from '../models/user';
const config = require('../config/auth.config');
const bcyrpt = require('bcrypt');
const jwt = require('jsonwebtoken');
class Auth {
    async register(req, res) {
        try {
            const dataUser = {
                name: req.body.name,
                email: req.body.email,
                username: req.body.username,
                password: bcyrpt.hashSync(req.body.password, 10)
            }
            const result = await new User(dataUser).save();
            if (result) {
                res.status(200).json(formatResponseSuccess(result, true, 'Thêm thành công'));
            }
        } catch (error) {
            console.log('register', error);
            return res.status(400).json(formatResponseError({ code: '404' }, false, 'Error'));
        }
    }


    async login(req, res) {
        try {
            const { username, password } = req.body;

            const user = await User.findOne({ username });
            if (!user) {
                return res.status(404).json(formatResponseError({ code: '404' }, false, 'Tên đăng nhập không tồn tại'));
            }
            const passwordMatch = await bcyrpt.compare(password, user.password);
            if (!passwordMatch) {
                return res.status(401).json(formatResponseError({ code: '401' }, false, 'Mật khẩu không chính xác'));
            }

            const accessToken = jwt.sign({ id: user.id }, config.secret, {
                // expiresIn: '24h' 
            });

            res.status(200).json(formatResponseSuccess({ user, accessToken }, true, 'Đăng nhập thành công'));
        } catch (error) {
            console.log('login', error);
            return res.status(400).json(formatResponseError({ code: '404' }, false, 'Error'));
        }

    }

    async verifyToken(req, res, next) {
        let token = req.headers["x-access-token"];
        if (!token) {
            return res.status(403).send({ message: "No token provided!" });
        }

        jwt.verify(token, config.secret, (err, decoded) => {
            if (err) {
                return res.status(401).send({ message: "Unauthorized!" });
            }
            req.userId = decoded.id;
            next();
        });
    }

}
export default new Auth();