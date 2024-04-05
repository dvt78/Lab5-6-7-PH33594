const mongoose = require("mongoose");
const fruitSchema = new mongoose.Schema({
    name: {
        type: String,
    },
    quantity: {
        type: String,
    },
    price: {
        type: Number,
    },
    status: {
        type: Number,
        default: 1
    },
    description: {
        type: String,
    },
    idUser: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    },
    idCompany: {
        type: Number,
        default: 1
    },
    images: []
})
module.exports = mongoose.model("Fruit", fruitSchema);