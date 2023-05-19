import casparser
import sys

filename = sys.argv[1]
password = sys.argv[2]

# Get data in json format
json_str = casparser.read_cas_pdf(filename,password, output="json")

print(json_str)
