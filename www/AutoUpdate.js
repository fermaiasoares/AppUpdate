var exec = require('cordova/exec');

exports.checkUpdate = function (arg0, success, error) {
    exec(success, error, 'AutoUpdate', 'checkUpdate', [arg0]);
};
