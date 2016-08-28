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
            solders:     req.body.soldiers,
            name:        req.body.name,
            homeAddress: req.body.homeAddress 
        }
        
        // Check whether the soldier already exist
        db.collection('Users').find({_id : req.body.soldierNumber})
        .toArray(function(err, docs) 
        {
            if (docs[0] != null){
            res.send("Soldier " + req.body.soldierNumber + " already exist");
            db.close();
            }
            else
            {
                db.collection('Users').insertOne( user , function(err,result){
               db.close();
                } )
            }
        }
                    
        );
    }
    )
}

module.exports = usersBL;