# java-filmorate
LINK:
https://app.quickdatabasediagrams.com/#/d/PbpCrx
![img_2.png](img_2.png)
Examples of requests:
```
SELECT * FROM user WHERE user_ID=1;

SELECT * FROM user

SELECT * FROM film WHERE film_ID=film_ID

SELECT * FROM user AS u WHERE user_ID in (
 SELECT friend_ID FROM friends AS f WHERE u.user_ID=f.user_ID
)
```