var express = require('express');
var router = express.Router();
var eventsBL = require("../BL/eventsBL");

/* GET users listing. */
router.get('/:fromTelNumber', function(req, res, next) {
    eventsBL.eventsOfUser(req, res);
});

router.post('/findEventsNearMe', function(req, res, next) {
    eventsBL.findEventsNearMe(req, res);
});

router.post('/createEvent', function(req,res,next){
    eventsBL.createEvent(req,res);
})



module.exports = router;
