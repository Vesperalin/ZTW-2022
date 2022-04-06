const { gql } = require('apollo-server-express');


const typeDefs = gql`
    type ToDoItem{
        id: ID!
        title: String!
        completed: Boolean!
        userId: String!
        user: User!
    } 

    type User{
        id: ID!
        name: String!
        email: String!
        login: String!
        todos: [ToDoItem!]!
    } 

    type Query {
        hello: String!
        todos: [ToDoItem!]
        todo(id: ID!): ToDoItem
        users: [User!]
        user(id: ID!): User
    } 

    input UserInput {
        name: String, 
        email: String, 
        login: String
    }

    input ToDoItemInput {
        title: String, 
        completed: String, 
        userId: String
    }

    type Mutation {
        createUser(user: UserInput): User,
        deleteUser(id: ID): String,
        updateUser(id: ID, user: UserInput): User,

        createToDoItem(todo: ToDoItemInput): ToDoItem,
        deleteToDoItem(id: ID): String,
        updateToDoItem(id: ID, todo: ToDoItemInput): ToDoItem,
    }

`;

module.exports = typeDefs;
