SELECT `film`.`film_id` AS `id`, `title`, `description`, `release_year`, `name` AS `language`, `director`, `rating`, `special_features`
FROM `film` JOIN `language` JOIN
(	
	SELECT `film_id`, CONCAT(`first_name`," ", `last_name`) AS `director`
	FROM `actor` JOIN `film_actor`
	ON `film_actor`.`actor_id` = `actor`.`actor_id`
) AS `film_dir`
ON `language`.`language_id` = `film`.`language_id` AND `film`.`film_id` = `film_dir`.`film_id`
ORDER BY `id`