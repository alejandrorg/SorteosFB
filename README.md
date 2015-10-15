# Sorteos FB

This software has been developed to create contests using Facebook data of a "fan page".

For example: You want to raffle an award to your page users. This tool allows you to do it.

Current version only allows to raffle based on the likes provided by users to a concrete post of the page. The software uses the access_token
of the page (you have to get it from http://developers.facebook.com, Tools & Support -> Graph API explorer. In "Get Token", you select your page
and get the token. This token is only valid for a small amount of time.

The software retrieves the most N recent posts (you specifiy N value) and you can select from these posts which one is the one used to get the user likes.

After selecting the post, you can establish the "magic number" parameter. This parameter (let me called it from now on MN) should be retrieved in real time from 
a random webpage such as random.org and it is used to generate "MN" pre-values.

1) The parameter is randomly choosed by random.org
2) The parameter establish the number of pre-values which will be created before get the winner.

For example, if MN = 4, the program will generate 4 users before select a winner. The fifth user will be the real winner.

It is important to remark that the users that are selected before (these 4 users) are still in the "rotating drum" which contains the names of the users. It don't discard them.

As said, the MN parameter is randomly choose, and the use of this parameter guarantee (in some way) that there is no hard-coded a given user to be the winner.

In any case, author recommend to execute a couple of tests and show the behaviour before doing the real contest.

v0.1: First version
