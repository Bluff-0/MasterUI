UPDATE `film`
SET `title`= ?, `description`= ?, `release_year`= ?, 
`language_id`= (SELECT `language_id` FROM `language` WHERE `name`= ?),
`rating`= ?, `special_features`= ?, `director`= ?
WHERE `film_id`= ?