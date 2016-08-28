var MongoAccess = require("../Dal/MongoQuery");
var DBName = 'Events';

var eventsBL = {};

eventsBL.eventsOfUser = function(req, res)
{
    MongoAccess.getDB(function(db)
    {
        db.collection(DBName).find({from : req.params.fromTelNumber})
        .toArray(function(err, docs) 
        {
            res.send(docs);
            db.close();
        })
    });    
};

eventsBL.findEventsNearMe = function(req, res) 
{
    req.body.location = JSON.parse(req.body.location);

    MongoAccess.getDB(function(db)
    {
        var collection = db.collection(DBName);
        collection.find( 
        { 
            "from" : req.body.userTelNum,
            "location":{                
                    "$near":
                    {
                        "$geometry":
                        {
                            "type" : "Point",
                            "coordinates": [ req.body.location.x , req.body.location.y ]
                        },
                        "$maxDistance" : 100,
                    }
            }
        }).toArray(function(err, results)
        {
            res.send(results);    
        });
  })
};

module.exports = eventsBL;