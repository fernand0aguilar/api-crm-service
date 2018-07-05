INSERT INTO `shop` (`id`, `shop_name`, `create_date`, `update_date`)
VALUES (NULL, 'Agile Monkeys', CURRENT_DATE(), CURRENT_DATE());

INSERT INTO users (id, name, surname, email, password, profile, create_date, update_date, shop_id, parent_email)
VALUES (NULL, 'John', 'Doe', 'admin@admin.com', 
'$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', 
'ROLE_ADMIN', CURRENT_DATE(), CURRENT_DATE(), (SELECT `id` FROM `shop` WHERE `shop_name` = 'Agile Monkeys') ,'father@user.com');


