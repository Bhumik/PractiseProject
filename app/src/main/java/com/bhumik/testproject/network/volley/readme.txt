We usually develop Android application of the time inevitably require the use of internet,
and in most cases the application will use the HTTP protocol to send and receive network data.
Android system mainly provides two ways to perform HTTP communication,
HttpURLConnection and HttpClient,
almost any item in the code, we can see the shadow of these two classes, the utilization rate is very high.

But HttpURLConnection and HttpClient usage is somewhat complicated, if not properly packaged,
then it's easy to write a lot of code duplication.

Consequently, some of the Android network communications framework will come into being, such as
AsyncHttpClient, it's all the HTTP communication details of all encapsulated in the interior,
we simply need to call a few lines of code to complete the communication operation.
Another example is the Universal-Image-Loader,
it makes the image displayed on the screen of the network operation becomes extremely simple,
the developer need not be concerned about how to get the pictures from the Internet, you do not care about open thread,
recycling resources, pictures and other details, Universal-Image- Loader has everything ready.

Android development team is aware of the need to HTTP communication operations further simplified,
so in the 2013 Google I / O conference to launch a new network communications framework --Volley.
Volley but that is the advantage AsyncHttpClient and Universal-Image-Loader is set in a suit,
either as very simple as AsyncHttpClient HTTP communication may also like Universal-Image-Loader as easily load images on the web.
In addition to ease of use,
Volley in performance has also been a significant adjustment,
its design goal is very suitable to the amount of data is small, but frequent communications network operations,
and for the large data volume of network operations, such as said download files, Volley performance will be very bad.




