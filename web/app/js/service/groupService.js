'use strict';

app.factory('GroupService', function() {  
    
    var has = function(obj, target) {
        return _.any(obj, function(value) {
            return _.isEqual(value, target);
        });
    };

    var keys = function(data, names) {
        return _.reduce(data, function(memo, item) {
            var key = _.pick(item, names);
            if (!has(memo, key)) {
                memo.push(key);
            }
            return memo;
        }, []);
    };
    
    var saldo = function(data, stem) {
        var saldo = 0;
        _.map(_.where(data, stem), function(item) {
            saldo += item.valor;
        });
        return saldo;
    }

    var group = function(data, names) {
        var stems = keys(data, names);
        return _.map(stems, function(stem) {            
            return _.extend(stem, {'valor': saldo(data, stem)});
        });
    };

    group.register = function(name, converter) {
        return group[name] = function(data, names) {
            return _.map(group(data, names), converter);
        };
    };
    
    group.register("saldo", function(item) {
        return item;
     });

    return group;

});