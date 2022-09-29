var exec = require('cordova/exec');

exports.checkUpdate = function (arg0, success, error) {
    exec(success, error, 'AppUpdate', 'checkUpdate', [arg0]);
};
