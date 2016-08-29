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
                            "coordinates": [ parseFloat(req.body.x) ,parseFloat(req.body.y) ]
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

// Required info: commanderNumber, message
eventsBL.createEvent = function(req,res)
{
    MongoAccess.getDB(function(db)
    {
        // Get commander's soldiers
        db.collection('Users').find({ commanderId: req.body.commanderNumber })
            .toArray(function(err, soldiers)
            {
                if (soldiers == null)
                {
                    res.send("This commander has no soldiers");
                }
                // In case he has more than 1
                else
                {
                    soldiers.forEach(function(soldier)
                    {                        
                        var event = 
                        {
                            from:     soldier._id,
                            to:       req.body.commanderNumber,
                            location: soldier.homeAddress,
                            message:  req.body.message
                        }

                        db.collection(DBName).insertOne(event, function(err,result)
                        {
                            db.close();
                        });
                    });

                }
            });
        });
    };   


module.exports = eventsBL;