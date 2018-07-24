import requests
from requests.auth import HTTPBasicAuth

"""
In some cases you may want to remove yourself from a different user's
private repository in bitbucket.  This may be useful in cases you have
access to "dead" repositories, which owners stopped using bitbucket.
And there's no straightforward way to remove yourself from these repos.
This (python3) code revokes access from repositories you don't want to
work with and don't want to see these repos in your account. Specify
username of unwanted repo owner and unwanted repo name below in the
variables. Also specify your username and password to the bitbucket
(attlassian) account. Then run this script. You are free to share this
script with anyone :)
"""

username = ""
password = ""

repoName = "cit360"
repoOwners = [
	"",
	"",
	""
]

credentials = HTTPBasicAuth(username, password)
for repoOwner in repoOwners:
	actionURL = "https://bitbucket.org/xhr/" + repoOwner + "/" + repoName + "/revoke"
	print(actionURL)
	response = requests.post(actionURL, auth=credentials)
	print(response.text)
