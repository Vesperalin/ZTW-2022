const { ApolloServer, gql } = require('apollo-server');
const axios = require("axios");

// A map of functions which return data for the schema.
const resolvers = {
    Query: {
        users: async () => getRestUsersList(),
        todos: async () => getRestToDoItemsList(),
        todo: async (parent, args, context, info) => todoById(parent, args, context, info),
        user: async (parent, args, context, info) => userById(parent, args, context, info),
    }, 
    User: 
    {
        todos: async (parent, _args, _context, _info) => {
            const allTodos = await getRestToDoItemsList();
            return allTodos.filter(t => t.userId == parent.id);
        }
    },
    ToDoItem:{
        user: async (parent, _args, _context, _info) => {
            const allUsers = await getRestUsersList();
            return allUsers.find(u => u.id == parent.userId);
        }
    }
};

async function todoById(_parent, args, _context, _info)
{
    const todosList = await getRestToDoItemsList();
    return todosList.find(t => t.id == args.id);
};

async function userById(_parent, args, _context, _info)
{
    const usersList = await getRestUsersList();
    return usersList.find(u => u.id == args.id);
};

async function getRestUsersList(){
    try {
    const users = await axios.get("https://jsonplaceholder.typicode.com/users")
    // console.log(users);
    return users.data.map(({ id, name, email, username, todos }) => ({
        id: id,
        name: name,
        email: email,
        login: username,
        todos: todos
    }))
    } catch (error) {
        throw error
    }
};
   
async function getRestToDoItemsList(){
    try {
    const todos = await axios.get("https://jsonplaceholder.typicode.com/todos")
    // console.log(todos);
    return todos.data.map(({ id, title, completed, userId }) => ({
        id: id,
        title: title,
        completed: completed,
        userId: userId
    }))
    } catch (error) {
        throw error
    }
};
   

const typeDefs = require("./schema");

const server = new ApolloServer({
  typeDefs, 
  resolvers,
});

server.listen().then(({ url }) => {
  console.log(`🚀 Server ready at ${url}`);
});
