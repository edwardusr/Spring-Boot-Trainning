<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Jump to this page after login verification</title>
</head>
<body>
    Jump to this page after login verification
    <div>
        <title>HOME</title>
    </div>
    <div>
        <a href="/article">Go to article page</a>
    </div>
    <div>
        <a href="/setting">Go to settings page</a>
    </div>
    <div>
        <a href="/logout">quit</a>
    </div>
    <hr/>
    <div>
        <title style="color:red;">Note that the permission is included below, the second link is not visible because there is no authorization</title>
    </div>
    <@shiro.hasPermission name="app:article:article">
    <div shiro:hasPermission="app:article:article">
        <a href="/article">Go to article page</a>
    </div>
    </@shiro.hasPermission>
    <BR><BR>=====================<BR>
    <@shiro.hasPermission name="app:article:setting">
    <div>
        <a  href="/setting">Go to settings page</a>
    </div>
    </@shiro.hasPermission>
</body>
</html>