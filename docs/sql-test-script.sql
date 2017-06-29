CREATE SCHEMA `test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ;

CREATE  TABLE `test`.`t_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'PK' ,
  `loginname` VARCHAR(45) NOT NULL COMMENT '登录名称' ,
  PRIMARY KEY (`id`) )
COMMENT = '用户表';

INSERT INTO `test`.`t_user` (`id`, `loginname`) VALUES ('1', 'milton');
