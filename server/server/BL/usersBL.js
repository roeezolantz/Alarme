var MongoAccess = require("../Dal/MongoQuery");

var usersBL = {};

usersBL.getUserById = function(req, res)
{
    MongoAccess.getDB(function(db)
    {
        db.collection('Users').find({_id : req.params.soldierNumber})
        .toArray(function(err, docs) 
        {
            res.send(docs);
            db.close();
        })
    });    
};

module.exports = usersBL;