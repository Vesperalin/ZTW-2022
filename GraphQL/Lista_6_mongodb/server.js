const express = require('express');
const { ApolloServer, gql } = require('apollo-server-express');

const typeDefs = require('./typeDefs');
const resolvers = require('./resolvers');
const mongoose = require('mongoose');

async function startServer() {
    const app = express()
    const apolloServer = new ApolloServer({
        typeDefs,
        resolvers
    });

    await apolloServer.start();
    apolloServer.applyMiddleware({app: app });

    app.use((req,res) => {
        res.send("Hello form express apollo server");
    });

    await mongoose.connect('mongodb://localhost:27017/ztw_db', {
        useUnifiedTopology: true,
        useNewUrlParser: true,
    });
    console.log('Mongoose connected...');


    app.listen(4000, ()=> console.log(`🚀 Server ready at port 4000`))

};
startServer();

