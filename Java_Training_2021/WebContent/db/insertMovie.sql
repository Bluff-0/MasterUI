INSERT INTO 
`film`(`title`,`description`,`release_year`,`language_id`,`rating`,`special_features`,`director`) 
VALUES( 
	?, ?, ?,
	(SELECT `language_id` FROM `language` WHERE `name`= ?),
	?, ?, ?)