from recipe_scrapers import scrape_me
import re
import json

scraper = scrape_me('https://www.marmiton.org/recettes/recette_tacos-mexicains_34389.aspx')

def checkKey(name, baseUnit, parse, unit):
    if parse in name:
        return name[len(parse):], unit
    return name, baseUnit

rules = [
    ["g de ", "g"],
    ["g d'", "g"],
    ["kg de", "kg"],
    ["kg d'", "kg"],
    ["cl de ", "cl"],
    ["cl d'", "cl"],
    ["pincées de ", "pincée"],
    ["pincées d'", "pincée"],
    ["pincée de ", "pincée"],
    ["pincée d'", "pincée"],
    ["c.à.c de ", "c.à.c"],
    ["c.à.c d'", "c.à.c"],
    ["c.à.s de ", "c.à.s"],
    ["c.à.s d'", "c.à.s"],
    ["cuillères de ", "cuillère"],
    ["cuillères d'", "cuillère"],
    ["cuillère de ", "cuillère"],
    ["cuillère d'", "cuillère"],
    ["bonne cuillère de ", "cuillère"],
    ["bonne cuillère d'", "cuillère"],
    ["bonnes cuillères d'", "cuillère"],
    ["bonnes cuillères de ", "cuillère"],
    ["verre de ", "verre"],
    ["verre d'", "verre"],
    ["verres de ", "verre"],
    ["verres d'", "verre"],
    ["ml de ", "ml"],
    ["ml d'", "ml"],
    ["cl de ", "cl"],
    ["cl d'", "cl"],
    ["l de ", "l"],
    ["l d'", "l"],
    ["petites boîtes de ", "boîte"],
    ["petites boîtes d'", "boîte"],
    ["petite boîte de ", "boîte"],
    ["petite boîte d'", "boîte"],
    ["boîtes de ", "boîte"],
    ["boîtes d'", "boîte"],
    ["boîte de ", "boîte"],
    ["boîte d'", "boîte"],
    ["paquets", "paquet"],
    ["paquet", "paquet"],
    ["branches de ", "branche"],
    ["branches d'", "branche"],
    ["branche de ", "branche"],
    ["branche d'", "branche"],
    ["sachets de ", "sachet"],
    ["sachets d'", "sachet"],
    ["sachets de ", "sachet"],
    ["sachet d'", "sachet"],
    ["sachet d'", "sachet"],
    ["brins d'", "brin"],
    ["brins de ", "brin"],
    ["brin d'", "brin"],
    ["brin d'", "brin"],
    ["belle tranche de ", "tranche"],
    ["belle tranche d'", "tranche"],
    ["belles tranches de ", "tranche"],
    ["belles tranches d'", "tranche"],
    ["tranches d'", "tranche"],
    ["tranches de ", "tranche"],
    ["tranche d'", "tranche"],
    ["tranche d'", "tranche"],
    ["jus d'", "jus"],
    ["jus de ", "jus"],
    ["poignée de ", "poignée"],
    ["poignée d'", "poignée"],
    ["poignées de ", "poignée"],
    ["poignées d'", "poignée"],
    ["petit pot de ", "petit pot"],
    ["petit pot d'", "petit pot"],
    ["petits pots de ", "petit pot"],
    ["petits pots d'", "petit pot"],
    ["pavé de ", "pavé"],
    ["pavé d'", "pavé"],
    ["pavés de ", "pavé"],
    ["pavés d'", "pavé"],
    ["grains de ", "grains"],
    ["grains d'", "grains"],
    ["bûches de ", "bûche"],
    ["bûches d'", "bûche"],
    ["bûche de ", "bûche"],
    ["bûche d'", "bûche"],
    ["filets de ", "filet"],
    ["filets d'", "filet"],
    ["filet de ", "filet"],
    ["filet d'", "filet"],
    ["feuilles de ", "feuille"],
    ["feuilles d'", "feuille"],
    ["feuille de ", "feuille"],
    ["feuille d'", "feuille"],
    ["gousses de ", "gousse"],
    ["gousses d'", "gousse"],
    ["gousse de ", "gousse"],
    ["gousse d'", "gousse"],
    ["petits morceaux de ", "petit morceau"],
    ["petits morceaux d'", "petit morceau"],
    ["petit morceau de ", "petit morceau"],
    ["petit morceau d'", "petit morceau"],
    ["sachets de ", "sachet"],
    ["sachets d'", "sachet"],
    ["sachet de ", "sachet"],
    ["sachet d'", "sachet"],
    ["briques de ", "brique"],
    ["briques d'", "brique"],
    ["brique de ", "brique"],
    ["brique d'", "brique"],
    ["boules de ", "boule"],
    ["boules d'", "boule"],
    ["boule de ", "boule"],
    ["boule d'", "boule"],
    ["barquettes de ", "barquette"],
    ["barquettes d'", "barquette"],
    ["barquette de ", "barquette"],
    ["barquette d'", "barquette"],
    ["escalopes de ", "escalope"],
    ["escalopes d'", "escalope"],
    ["escalope de ", "escalope"],
    ["escalope d'", "escalope"],
    ["gros morceaux de ", "gros morceau"],
    ["gros morceaux d'", "gros morceau"],
    ["gros morceau de ", "gros morceau"],
    ["gros morceau d'", "gros morceau"],
    ["briquettes de ", "briquette"],
    ["briquettes d'", "briquette"],
    ["briquette de ", "briquette"],
    ["briquette d'", "briquette"],
]

def parseIngName(name):
    n = name
    unit = None
    if n[-1] == " ":
        n = n[:-1]
    for r in rules:
        n, unit = checkKey(n, unit, r[0], r[1])
        if unit:
            return n, unit
    if "de " in n[:3]:
        n = n[:3]
    if "d'" in n[:2]:
        n = n[:2]
    return n, unit

def parseIng(ingL, nbP):
    l = []
    magicRETemplate = "^([0-9./]+) ([^()]+)( \(.+\))*"
    magicRETemplate2 = "^([^()]+)( \(.+\))*"
    for ing in ingL:
        line = re.search(magicRETemplate, ing)
        try:
            quantity = eval(line.group(1))
        except Exception:
            quantity = -1
        if line is None or quantity == -1:
            line = re.search(magicRETemplate2, ing)
        name = line.group(2) if quantity != -1 else line.group(1)
        name, unit = parseIngName(name)
        l.append({
            "quantity": (quantity if quantity != -1 else 1)/nbP,
            "name": name,
            "unit": unit
            })
    return l

def formatting(s):
    nbP = re.search("[0-9]+", scraper.yields())
    return {
        "name": scraper.title(),
        "category": "Autre" if scraper.category() == "" else scraper.category(),
        "ingredients": parseIng(scraper.ingredients(), int(nbP.group(0))),
        "instructions": scraper.instructions_list(),
        "estimated_time": scraper.total_time(),
        "image": scraper.image(),
        "rating": scraper.ratings() if scraper.ratings() != 0.0 else -1.0
    }

visited_pages = ['https://www.marmiton.org/recettes/recette_tacos-mexicains_34389.aspx']

to_visit = [e["href"] for e in scraper.links() if "recettes/recette" in e["href"]]

recipes = [formatting(scraper)]

i = 1000
"""
{
    "name":"XX",
    "ingredients": [{
            "name":"XX",
            "quantity":-1
        }],
    "instructions": ["XX", "XX"],
    "estimated_time": -1,
    "image": "url",
    "rating": 0.0
}
"""

while len(to_visit) != 0 and i > 0:
    i-=1
    l = to_visit.pop()
    scraper = scrape_me(l)
    try:
        recipes.append(formatting(scraper))
    except:
        break
    visited_pages.append(l)
    to_visit.extend([e["href"] for e in scraper.links() if "recettes/recette" in e["href"] and e["href"] not in visited_pages])

with open("./parsed.json", 'w', encoding="latin-1", errors='backslashreplace') as f:
    lines = json.dumps(recipes, indent=4, ensure_ascii=False)
    f.write(lines.replace("œ", "oe").replace("’","'"))