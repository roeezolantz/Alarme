var MongoAccess = {};

MongoAccess.getDB = function(callback)
{
    var MongoClient = require('mongodb').MongoClient
                        , assert = require('assert');

    // Connection URL
    var url = 'mongodb://alarmi:alarmi@ds044699.mlab.com:44699/alarmidb';

    // Use connect method to connect to the server
    MongoClient.connect(url, function(err, db) {
        
        if(err == null)
        {
            callback(db);
        }

    });
};

module.exports = MongoAccess;