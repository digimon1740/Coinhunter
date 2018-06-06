CREATE SCHEMA `coinhunter` DEFAULT CHARACTER SET utf8;
grant create, select, insert, update, delete, drop on coinhunter.* to 'coinhunter'@'127.0.0.1' identified by 'zhak!@#';
flush privileges;

-- Used by Spring Remember Me API.
CREATE TABLE Persistent_Logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id`                BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name` varchar(100)  NOT NULL,
  `password` varchar(1000) NOT NULL,
  `email` varchar(200)  NOT NULL,
  `email_accepted` tinyint(1) NULL default '0',
  `cellphone` varchar(200) NULL,
  `role` enum('ROLE_USER','ROLE_ADMIN') default 'ROLE_USER',
  `approved` tinyint(1) NULL default '0',
  `last_login_time` TIMESTAMP   NULL,
  `reg_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `api_keys`;
CREATE TABLE IF NOT EXISTS `api_keys` (
  `id`                BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(100)  NOT NULL,
  `name` varchar(200) NOT NULL,
  `exchange` enum('BITHUMB','COINONE','UPBIT') default 'BITHUMB',
  `access_key` varchar(2000),
  `secret_key` varchar(2000),
  `last_modified_time` TIMESTAMP   NULL,
  `reg_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `watch_bot_list`;
CREATE TABLE IF NOT EXISTS `watch_bot_list` (
  `id`                BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(100)  NOT NULL,
  `api_key_id` BIGINT(100)  NOT NULL,
  `name` varchar(200) NOT NULL,
  `coinType` varchar(10) NOT NULL,
  `term` tinyint(1) NOT NULL default 5,
  `auto_trading` tinyint(1) NOT NULL default 0,
  `notified` tinyint(1) NOT NULL default 0,
  `position` enum('BUY','SELL') default 'BUY',
  `enabled` tinyint(1) NOT NULL default 0,
  `last_modified_time` TIMESTAMP   NULL,
  `reg_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `selling_strategies`;
CREATE TABLE IF NOT EXISTS `selling_strategies` (
  `id`                BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(100)  NOT NULL,
  `watch_bot_id` BIGINT(100)  NOT NULL,
  `strategy` text NOT NULL,
  `last_modified_time` TIMESTAMP   NULL,
  `reg_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `order_logs`;
CREATE TABLE IF NOT EXISTS `order_logs` (
  `id`                BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(100)  NOT NULL,
  `watch_bot_id` BIGINT(100)  NOT NULL,
  `coinType` varchar(10) NOT NULL,
  `order_method` enum('BUY','SELL') default 'BUY',
  `strategy` text NOT NULL,
  `quantity` decimal(10,4) NOT NULL,
  `avg_price` int(10) NOT NULL default 0,
  `trade_price` int(10) NOT NULL default 0,
  `income_fee` int(10) NOT NULL default 0,
  `last_modified_time` TIMESTAMP   NULL,
  `reg_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;