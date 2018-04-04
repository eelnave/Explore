/* select for last 24 hours */
SELECT latitude
,      longitude
FROM   location l
JOIN   report r
ON	   l.location_id = r.location_id
WHERE  report_date > DATE_SUB(NOW(), INTERVAL 24 HOUR);

/* SELECT for last 7 days */
SELECT latitude
,      longitude
FROM   location l
JOIN   report r
ON	   l.location_id = r.location_id
WHERE  report_date > DATE_SUB(NOW(), INTERVAL 7 DAY);

/* select for last 30 days */
SELECT latitude
,      longitude
FROM   location l
JOIN   report r
ON	   l.location_id = r.location_id
WHERE  report_date > DATE_SUB(NOW(), INTERVAL 30 DAY);

/* select for last 365 days */
SELECT latitude
,      longitude
FROM   location l
JOIN   report r
ON	   l.location_id = r.location_id
WHERE  report_date > DATE_SUB(NOW(), INTERVAL 1 YEAR);

/* select for all time */
SELECT latitude
,      longitude
FROM   location;