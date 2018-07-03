CREATE TABLE `shop` (
  `id` bigint(20) NOT NULL,  
  `shop_name` varchar(255) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profile` varchar(255) NOT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `parent_email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for table `shop`
--
ALTER TABLE `shop`
ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
ADD PRIMARY KEY (`id`),
ADD KEY `key` (`shop_id`);

--
-- AUTO_INCREMENT for table `shop`
--
ALTER TABLE `shop`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


--
-- Constraints for dumped tables
--
--
-- Constraints for table `users`
--
ALTER TABLE `users`	
ADD CONSTRAINT `key` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`);




