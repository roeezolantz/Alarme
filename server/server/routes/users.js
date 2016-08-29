var express = require('express');
var router = express.Router();
var usersBL = require("../BL/usersBL");



/* GET users listing. */
router.get('/:soldierNumber', function(req, res, next) {
    usersBL.getUserById(req, res);  
});

router.post('/createSoldier', function(req, res, next) {
    usersBL.createSoldier(req,res);
});

module.exports = router;