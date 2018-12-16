/**
 * @author wang extensions:
 *         https://github.com/y2w/bootstrap-table-treetable
 */

!function($) {
	'use strict';
	var sprintf = function(str) {
		var args = arguments, flag = true, i = 1;

		str = str.replace(/%s/g, function() {
			var arg = args[i++];

			if (typeof arg === 'undefined') {
				flag = false;
				return '';
			}
			return arg;
		});
		return flag ? str : '';
	};

	$.extend($.fn.bootstrapTable.defaults, {
		treetable : true,
		treeSelected :undefined,
		onTreeTableInit : function() {
			return false;
		},
		onTreeTableExpand : function(field, row) {
			return false;
		},
		onPreBody : function(data) {
			if (data.length == 0)
				return false;
			$.each(data, function(i, item) {
				if (!item._data) {
					item._data = {
						depth : item.layid?item.layid:1,
						pId :'',
						expand : false,
						hasChild :item.hasChild
					}
				}
			});
			return true;
		}
	});
	$.extend($.fn.bootstrapTable.defaults.icons, {
		'iconExpand' : 'glyphicon-minus',
		'iconCollapse' : 'glyphicon-plus'
	});
	$.extend($.fn.bootstrapTable.Constructor.EVENTS, {
		'treetable-init.bs.table' : 'onTreeTableInit',
		'treetable-expand.bs.table' : 'onTreeTableExpand'
	});

	var BootstrapTable = $.fn.bootstrapTable.Constructor, 
		_initTable = BootstrapTable.prototype.initTable, 
		_initBody = BootstrapTable.prototype.initBody,
		_methods = $.fn.bootstrapTable.methods;

	_methods.push("addTreeNode");
	BootstrapTable.prototype.initTable = function() {
		var that = this;
		_initTable.apply(this, Array.prototype.slice.apply(arguments));

		if (!this.options.treetable) {
			return;
		}
		$.each(this.options.columns[0],
			function(i, column) {
				if (!column.expanded) {
					return;
				}
				var _formatter = column.formatter;
				column.formatter = function(value, row, index) {

					var result = _formatter ? _formatter(value,row, index) : value,
						depth = 0, 
						icon = that.options.icons.iconCollapse;

					if (row._data && !$.isEmptyObject(row._data)) {
						depth = row._data.depth - 1;
						if (row._data.expand) {
							icon = that.options.icons.iconExpand;
						}
						if (!row._data.hasChild) {
							icon = '';
						}
					}
					var paddingLeft = 20 * depth;
					return [
							sprintf(
									'<a href="#"><span class="%s %s" data-name="%s" data-pk="%s" style="padding-left:%spx"></span></a>',
									that.options.iconsPrefix, icon,
									column.field,
									row[that.options.idField],
									paddingLeft), result].join('');
				};
		});
	};

	BootstrapTable.prototype.initBody = function() {
		var that = this;

		_initBody.apply(this, Array.prototype.slice.apply(arguments));

		if (!this.options.treetable) {
			return;
		}
		$.each(this.options.columns[0], function(i, column) {
			if (!column.expanded) {
				return;
			}
			that.$body.off('click','span[data-name="' + column.field + '"]').
			on('click','span[data-name="' + column.field + '"]',
			function(event) {
				var $this = $(this), 
					$tr = $this.parents('tr[data-index]'), 
					index = $tr.data('index'),
					uniqueid = $tr.data('uniqueid'),
					$children = that.$body.children('tr[data-parentid="'+ uniqueid + '"]');
					that.options.treeSelected=index;
				if ($tr.data("expand")) {
					var data = that.getData(),
					row = data[index];
				    that.data[index]._data.expand=false;
					$this.removeClass(that.options.icons.iconExpand).
					addClass(that.options.icons.iconCollapse);
					$.each($children, function(i, child) {
						var datas = getAllChildNode($(child),that.$body);
						$.each(datas, function(i, obj) {
							that.hideRow({index : $(obj).data("index")});
						})
						that.hideRow({index : $(child).data("index")});
					})
					return false;
				} else {
					
					$this.removeClass(that.options.icons.iconCollapse).
					addClass(that.options.icons.iconExpand);
					
					if($children && $children.length>0){
						$.each($children, function(i, child) {
							that.showRow({index : $(child).data("index")});
						})
						$tr.data("expand",true);
						return false;
					}	
				}
				var data = that.getData(),
					row = data[index];
				that.data[index]._data.expand=true;
				
				that.trigger('treetable-expand', column.field,row);
			});
			
			function getAllChildNode(node,parent){
			    var tempC = parent.children('tr[data-parentid="'+ $(node).data("uniqueid") + '"]');
                if(tempC!=null && tempC.length>0){
                	$.each(tempC, function(i, child) {
                		var datas = getAllChildNode($(child),parent)
                			$.each(datas, function(i, data) {
                				tempC.push(data);
						   });
					})
                }
                return tempC;
		}
	});
	this.trigger('treetable-init');
	};
				/*
					var $this = $(this), 
						$tr = $this.parents('tr[data-index]'), 
						index = $tr.data('index'),
						$children = that.$body.children('tr[data-pId="'+ index + '"]');
						that.options.treeSelected=index;
						var allOtherChildren = [];
						$.each($children, function(i, obj) {
							var datas = getAllChildNode($(obj),that.$body);
							$.each(datas, function(i, data) {
								allOtherChildren.push(data);
							});
						})
					if ($tr.data("expand")) {
						$this.removeClass(that.options.icons.iconExpand).
						addClass(that.options.icons.iconCollapse);
						$tr.data("expand",false);
						var data = that.getData();
					    that.data[index]._data.expand=false;
						$.each(allOtherChildren, function(i, child) {
							that.hideRow({index : $(child).data("index")});
						})
						$.each($children, function(i, obj) {
							that.hideRow({index : $(obj).data("index")});
						})
						return false;
					} else {
						$this.removeClass(that.options.icons.iconCollapse).
						addClass(that.options.icons.iconExpand);
						if($children && $children.length>0){
							$.each($children, function(i, child) {
								that.showRow({index : $(child).data("index")});
							})
							$tr.data("expand",true);
							var newTr = that.$body.children('tr[data-index="'+ index + '"]');
							var newThis = newTr.find('span[data-name="' + column.field + '"]');
							newThis.removeClass(that.options.icons.iconCollapse).
							addClass(that.options.icons.iconExpand);
							newTr.data("expand",true);
							return false;
						}	
					}
				
					var data = that.getData(),
						row = data[index];
					that.data[index]._data.expand=true;
					
					that.trigger('treetable-expand', column.field,row);
					
					function getAllChildNode(node,parent){
						    var tempC = parent.children('tr[data-pId="'+ $(node).data("index") + '"]');
	                        if(tempC!=null && tempC.length>0){
	                        	$.each(tempC, function(i, child) {
	                        		var datas = getAllChildNode($(child),parent)
	                        			$.each(datas, function(i, data) {
	                        				tempC.push(data);
									   });
	    						})
	                        }
	                        return tempC;
					}
				});*/

	BootstrapTable.prototype.addTreeNode = function(params) {
		if (!params.hasOwnProperty('row')) {
			return;
		}
		var that = this,
			index=that.options.treeSelected,
			$tr = that.$body.find('tr[data-index="' + index+ '"]'), 
			depth = $tr.data("depth"),
			data = params.row;
		
		if(data.length==0){
			that.data[index]._data.hasChild=false;
			return ;
		}

		for (var i = 0; i < data.length; i++) {
			var item=data[i];
			var _data = {
				'pId' : index,
				'parentid':item[that.options.parentid],
				'depth' : depth + 1,
				'expand' : false,
                'uniqueid':item[that.options.idField],
				 hasChild :item.hasChild
			};
			data[i]._data = _data;
			this.data.splice(index + i + 1, 0, data[i]);
		}
		this.initSearch();
		this.initPagination();
		this.initSort();
		this.initBody(true);
	};

}(jQuery);