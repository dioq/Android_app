// HOOK普通方法 (除了不指定参数类型外,跟hook Overload重载方法写法一样)
if(Java.available){
	Java.perform(function(){
		var util = Java.use("com.my.fridademo.Util");//获取到类
		util.ordinaryFunc.implementation = function(param1, param2, param3){
			console.log("Hook Start...");
			//打印堆栈
			console.log(Java.use("android.util.Log").getStackTraceString(Java.use("java.lang.Exception").$new()));
			//var stack_print = Java.use("android.util.Log").getStackTraceString(Java.use("java.lang.Exception").$new());
			//console.log(stack_print);
			send(arguments[0]);
			send(arguments[1]);
			send(arguments[2]);
			var handleParam = arguments[0] + arguments[1];
			send(handleParam);
			return "Hooked 后: 成功hook住";
		}
	});
}
