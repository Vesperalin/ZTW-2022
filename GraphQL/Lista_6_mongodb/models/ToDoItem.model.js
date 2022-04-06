const { Int32 } = require('bson');
const mongoose = require('mongoose');
const { Int16Array } = require('webidl-conversions');

const ToDoItemSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true,
    },
    completed: {
        type: Boolean,
        required: true,
    },
    userId: {
        type: String,
        required: true,
    },
});

const ToDoItem = mongoose.model('todoitem', ToDoItemSchema);
module.exports = ToDoItem;
