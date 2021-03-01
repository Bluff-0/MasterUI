SELECT `film_id`, `title`, `description`, `release_year`, `name` AS `language`, `director`, `rating`, `special_features`
FROM `film` JOIN `language`
ON `language`.`language_id` = `film`.`language_id`
ORDER BY `title`