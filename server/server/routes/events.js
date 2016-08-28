var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/:fromTelNumber', function(req, res, next) {
  res.send(req.params.fromTelNumber);
});

module.exports = router;
