const User = require('./models/User.model');
const ToDoItem = require('./models/ToDoItem.model');

const resolvers = {
    Query: {
        hello: () => { return 'Hello World'; },
        users: async () => {
            const users = await User.find()
            return users
        },
        user: async (_parent, {id}, _context, _info) => {
            return await User.findById(id);
        },
        todos: async (_parent, _args, _context, _info) => {
            const todos = await ToDoItem.find()
            return todos;
        },
        todo: async (_parent, {id}, _context, _info) => {
            return await ToDoItem.findById(id);
        },
    }, 
    User: 
    {
        todos: async (parent, _args, _context, _info) => {
            const allTodos = await ToDoItem.find();
            return allTodos.filter(t => t.userId == parent.id);
        }
    },
    ToDoItem:{
        user: async (parent, _args, _context, _info) => {
            const allUsers = await User.find();
            return allUsers.find(u => u.id == parent.userId);
        }
    },
    Mutation: {
        createUser: async (_parent, args, _context, _info) => {
            const { name, email, login } = args.user
            const user = new User({name, email, login})
            await user.save()
            return user;
        },
        deleteUser: async (_parent, args, _context, _info) => {
            const {id} = args
            await User.findByIdAndDelete(id)
            return "User deleted..."
        },
        updateUser: async (_parent, args, _context, _info) => {
            const { name, email, login } = args.user
            const {id} = args
            const user = await User.findByIdAndUpdate(
                id, 
                {name, email, login}, 
                {new: true}
                );
            return user;
        },
        createToDoItem: async (_parent, args, _context, _info) => {
            const { title, completed, userId } = args.todo
            const todo = new ToDoItem({title, completed, userId})
            await todo.save()
            return todo;
        },
        deleteToDoItem: async (_parent, args, _context, _info) => {
            const {id} = args
            await ToDoItem.findByIdAndDelete(id)
            return "ToDoItem deleted..."
        },
        updateToDoItem: async (_parent, args, _context, _info) => {
            const { title, completed, userId } = args.todo
            const {id} = args
            const todo = await ToDoItem.findByIdAndUpdate(
                id, 
                {title, completed, userId}, 
                {new: true}
                );
            return todo;
        },
    }
};

module.exports = resolvers;
