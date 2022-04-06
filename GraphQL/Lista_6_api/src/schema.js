const { gql } = require("apollo-server");

const typeDefs = gql`
  
 type ToDoItem{
    id: ID!
    title: String!
    completed: Boolean!
    userId: Int!
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
 todos: [ToDoItem!]
 todo(id: ID!): ToDoItem
 users: [User!]
 user(id: ID!): User
} 

`;

module.exports = typeDefs;
