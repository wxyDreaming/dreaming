package com.wuxinyu.utils;

import org.apache.commons.lang.StringUtils;

public class TaxUtils {
	/**
	 * 计算一件货物的税收
	 * 采用数据库函数计算 dbo.taxutil(50,50,'ZCOV','1203311111')
	 * @param orderType
	 *            订单类型
	 * @param shipTo
	 *            到柜台还是到office
	 * @param hierarchy
	 *            货物类型Saleable,Sample,tester,gift等
	 * @param retailPrice
	 *            零售价 retailPrice表中的amount
	 * @param cost
	 *            成本价,货物表中的cost Standardprice_AR/1000
	 * @return
	 */
	@Deprecated 
	public static double calculateTax(String orderType, String shipTo,
			String hierarchy, double retailPrice, double cost) {
		// ZCOV
		if (orderType.equalsIgnoreCase("ZCOV")) {
			// Saleable类型货物税收
			if (hierarchy.equalsIgnoreCase("Saleable")) {
				// 公式:Retail price / 1.17 * 0.75 * 0.17
				return retailPrice / 1.17 * 0.75 * 0.17;
			} else if (hierarchy.equalsIgnoreCase("GWP")) {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			} else if (hierarchy.equalsIgnoreCase("Sample")
					|| hierarchy.equalsIgnoreCase("Tester")) {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			} else if (hierarchy.equalsIgnoreCase("Collateral")) {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			} else {
				// 没有找到货物类型的货物
				return cost * 1.1 * 0.17;
			}
		} else if (orderType.equalsIgnoreCase("ZCO")) {
			// ZCO类型
			return 0;
		} else if (orderType.equalsIgnoreCase("ZTST")) {
			// ZTST类型
			// 公式:COG
			return cost;
		} else if (orderType.equalsIgnoreCase("ZFRV")) {
			// ZFRV类型
			if (hierarchy.equalsIgnoreCase("Saleable")) {
				// 公式:Retail price / 1.17 * 0.75 * 0.17
				return retailPrice / 1.17 * 0.75 * 0.17;
			} else if (hierarchy.equalsIgnoreCase("GWP")) {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			} else if (hierarchy.equalsIgnoreCase("Sample")
					|| hierarchy.equalsIgnoreCase("Tester")) {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			} else {
				return cost * 1.1 * 0.17;
			}
		} else if (orderType.equalsIgnoreCase("ZPOS")) {
			// 公式:Retail price / 1.17 * 0.75 * 0.17
			return retailPrice / 1.17 * 0.75 * 0.17;
		} else if (orderType.equalsIgnoreCase("ZPOP")) {
			if (hierarchy.equalsIgnoreCase("GWP")) {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			} else if (hierarchy.equalsIgnoreCase("Sample")) {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			} else if (hierarchy.equalsIgnoreCase("Collateral")) {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			} else {
				// 公式:COGS * 1.1 * 0.17
				return cost * 1.1 * 0.17;
			}
		} else if (orderType.equalsIgnoreCase("ZOPS")) {
			// 没有税收
			return 0;
		} else {
			return 0;
		}
	}
	/**
	 * 根据货物的productHierarchy判断是那种类型的货物 Saleable,gift,tester等
	 * @param productHierarchy
	 * @return 货物类型
	 */
	public static String findMaterialType(String productHierarchy){
		if(productHierarchy.startsWith("11010")){
			return "Saleable";
		}else if(productHierarchy.startsWith("11011")){
			return "Saleable-PWP";
		}else if(productHierarchy.startsWith("12030")){
			return "Sample";
		}else if(productHierarchy.startsWith("12031")){
			return "Tester";
		}else if(productHierarchy.startsWith("12032")){
			return "Gifts";
		}else if(productHierarchy.startsWith("12033")){
			return "Salon";
		}else if(productHierarchy.startsWith("12034")){
			return "In House";
		}else if(productHierarchy.startsWith("12035")){
			return "Tester Vials";
		}else if(productHierarchy.startsWith("130")){
			return "Collateral";
		}else if(productHierarchy.startsWith("140")){
			return "Non-Inventory Materials";
		}else {
			return StringUtils.EMPTY;
		}
	}
	
}
