window.addEventListener('load', function (event) {
  function getBestMatch() {
    var outputs = document.getElementsByClassName('output');
    var names = [];
    for (var i = 0; i < outputs.length; i++) {
      if (outputs[i].innerText.trim() != '') {
        names[i] = outputs[i].innerText;
      }
    }

    const mostFrequent = data => data.reduce((r, c, i, a) => {
      r[c] = (r[c] || 0) + 1
      r.max = r[c] > r.max ? r[c] : r.max
      if (i == a.length - 1) {
        r = Object.entries(r).filter(([k, v]) => v == r.max && k != 'max')
        return r.map(x => x[0])
      }
      return r
    }, { max: 0 })

    var bestMatch = '<p>&nbsp;</p>';
    if (names.length > 0) {
      bestMatch = '<p>Best match: <strong>' + mostFrequent(names).join(', ') + '</strong></p>';
    }
    document.getElementById('best-match').innerHTML = bestMatch;
  }

  function getMatch(id) {
    var locationId = document.getElementById('location').value;
    var score = document.getElementById('slider-' + id).value;
    //var url = '/api/area/search?locationId=' + locationId + '&categoryId=' + id + '&score=' + score;
    var url = 'http://localhost:9090/api/area/search?locationId=' + locationId + '&categoryId=' + id + '&score=' + score;
    fetch(url)
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          return { "name": "" };
        }
      })
      .then(data => {
        document.getElementById('slider-' + id + '-output').innerHTML = data.name;
        getBestMatch();
      })
  }

  function getCategories() {
    //var url = '/api/category';
    var url = 'http://localhost:9090/api/category';
    fetch(url)
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          return [];
        }
      })
      .then(data => {
        var categoriesHtml = '';
        data.forEach(cat => {
          categoriesHtml +=
            '<div class="row">' +
            '<div class="column column-20"><p>' + cat.name + '</p></div>' +
            '<div id="container-slider-' + cat.id + '" class="column-40 container-slider">' +
            '<input id="slider-' + cat.id + '" type="range" min="1" max="5" value="3" class="slider">' +
            '</div>' +
            '<div class="column column-20">' +
            '<div id="slider-' + cat.id + '-output" class="column output"></div>' +
            '</div>' +
            '</div>';
        });
        document.getElementById('categories').innerHTML = categoriesHtml;

        var sliders = document.getElementsByClassName('slider');
        for (var i = 0; i < (sliders.length); i++) {
          sliders[i].addEventListener('input', function () {
            var divId = this.getAttribute('id');
            var id = divId.substring(divId.length, divId.length - 1);
            getMatch(id);
          });
        }

      })
  }
  getCategories();
});