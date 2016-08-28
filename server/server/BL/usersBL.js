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

usersBL.createSoldier = function(req,res)
{
    MongoAccess.getDB(function(db)
    {
        var user = { 
            _id:         req.body.soldierNumber,
            type:        req.body.type,
            commanderId: req.body.commanderNumber,
            solders:     req.body.solders,
            name:        req.body.name
        }

        // TODO: Validation
        
        db.collection('Users').insertOne( user , function(err,result){
            db.close();
        }
        );
    
    }
    )
}

module.exports = usersBL;