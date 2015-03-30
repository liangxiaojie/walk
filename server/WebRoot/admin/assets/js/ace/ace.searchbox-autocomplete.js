/**
The autocomplete dropdown when typing inside search box.
<u><i class="glyphicon glyphicon-flash"></i> You don't need this. Used for demo only</u>
*/

var _g_items = [];

(function($ , undefined) {
	try {
		$('#nav-search-input').bs_typeahead({
			minLength: 2,
			source: function(query, process) {
				$.ajax({url: 'search?text='+encodeURIComponent(query)}).done(function(result_items){
					_g_items = result_items
				   result_items_map = result_items.map(function(v){ return v.APPNAME || v._id || v })
				   process(result_items_map, result_items)
				})
			},
			updater:function (item) {
				//when an item is selected from dropdown menu, focus back to input element
				$('#nav-search-input').focus()
				return item
			}, 
			urender: function(dom, index){
				var item = _g_items[index]
				dom.attr({ _id: item._id, type: item.TYPE })
				item.MENU ? dom.attr("menu", item.MENU) : 1;
				item.NEWMENU ? dom.attr("newmenu", item.NEWMENU) : 1;
			},
			uselect: function(e){
				var target = e.target;
				var $active = $(target).parent(".active") 
				$active.length == 0 ? $active = $(target).parent("a").parent(".active") : 1
				selectok($active)
			}	
		})
	} catch(e) {}
	
	function selectok($active){
		var type = $active.attr("type")
		var _dynamic_id = $active.attr("_id")
		relocation( parseInt(type), _dynamic_id )
		window._dynamic_id = _dynamic_id
		sessionStorage._dynamic_id = _dynamic_id
		
		if(type == 1){
			var menu = $active.attr("menu")
			var html = '<li class="flexible"><a href="#">应用参考</a></li><li class="flexible"><a href="#">旧版门户</a></li>';
			if(menu){
				var sa = menu.split(",");
				sa = _(sa).uniq();
				for(var i in sa){
					html += '<li class="flexible"><a href="#">'+sa[i]+'</a></li>'
				}
			}
			
			var newmenu = $active.attr("newmenu")
			if(newmenu){
				html += '<li class="flexible active">//</li><li class="flexible active"><a href="#">新版门户</a></li>';
				sa = newmenu.split(",")
				for(var i in sa){
					html += '<li class="flexible"><a href="#">'+sa[i]+'</a></li>'
				}
			}
			
			$("#navigation .flexible").remove()
			$("#navigation").append(html)
		}else if(type == 0){
			var html = '<li class="flexible"><a href="#">'+_dynamic_id+'</a></li>'
			$("#navigation .flexible").remove()
			$("#navigation").append(html)
		}else if(type == 2){
			var menu = $active.attr("menu")
			var html = '<li class="flexible"><a href="#">应用参考</a></li>';
			if(menu){
				var sa = menu.split(",");
				sa = _(sa).uniq();
				for(var i in sa){
					html += '<li class="flexible"><a href="#">'+sa[i]+'</a></li>'
				}
			}
			
			$("#navigation .flexible").remove()
			$("#navigation").append(html)
		}
	}

	$("#nav-search-input").keyup(function(e){
		if(e.which == 13){
			var $active = $("ul.typeahead li[class*=active]")
			var datavalue = $active.attr("data-value")
			$("#nav-search-input").val(datavalue)
			selectok($active)
		}
	});

})(window.jQuery)