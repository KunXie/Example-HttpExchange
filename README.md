# Example-HttpExchange
this is a example code snippet of HttpExchange annotations

user cases:
* normal get request with path params and query params
* dynamically generate url with get requests
* normal post requests with path params and query params, and post body
* dynamically set headers
* TODO: logging - writing customer filters for now, maybe there are better solution later.
* TODO: error handling
  * `@ControllerAdvice` + `@ExceptionHandler` is not working for the reactive WebClient
  * using try-catch block for now

##

Method Arguments:
* URI
* HttpMethod
* @RequestHeader
* @PathVariable
* @RequestBody
* @RequestParam
* @RequestPart
* @CookieValue

Method returns:
* void
* String - string of ResponseBody
* POJO class - result of jackson deserialization of ResponseBody
* HttpHeader
* ResponseEntity<?>
* Mono<?> - non-blocking mode, all above are blocking mode.

### dependency

* https://httpbin.org/
* [HttpExchange doc](https://docs.spring.io/spring-framework/docs/6.0.0-RC3/javadoc-api/org/springframework/web/service/annotation/HttpExchange.html)
