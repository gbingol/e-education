--Air leaving the cooling section of an air conditioning system at T1C 
--and RH1 % relative humidity at a rate of V1 (m3/min) is mixed adiabatically with outside air at T2C and RH2 % relative humidity 
--at a rate of V2 (m3/min).  Assuming that the mixing process occurs at a pressure of P(atm), 
--determine the relative humidity, the dry-bulb temperature, and the volume flow rate of the mixture.
local function Compute(tbl)
	local T1, T2=tbl.T1, tbl.T2 --C
	local RH1, RH2=tbl.RH1, tbl.RH2 --%
	local V1, V2=tbl.V1, tbl.V2 --m3/min
	local Ptotal=tbl.P -- kPa


	local state1=psychrometry{Tdb=T1, RH=RH1, P=Ptotal} -- First inlet stream
	local state2=psychrometry{Tdb=T2, RH=RH2, P=Ptotal} -- Second inlet stream

	local v1, v2=state1.V, state2.V -- m3/kg da
	local w1, w2=state1.W, state2.W -- kg/ kg da
	local h1, h2=state1.H, state2.H -- kJ/kg da

	local ma1, ma2=V1/v1, V2/v2 --kg da

	local ma3=ma1+ma2 --kg da
	local mwater=w2*ma2+w1*ma1 -- amount of water vapor in the exit stream
	local w3=mwater/ma3 -- absolute humidity of exiting air
	local E3=h2*ma2+h1*ma1 -- enthalpy of exiting air, kJ
	local h3=E3/ma3 -- enthalpy of exiting air, kJ/kg da

	--Now that we know at least 3 properties of the exiting air, we can compute the rest
	local state3=psychrometry{P=Ptotal, W=w3, H=h3}

	--Volumetric flow rate of exiting air
	local V3=state3.V*ma3
	local RH3=state3.RH
	local T3=state3.Tdb
	
	local retTbl={}
	retTbl.V=V3; retTbl.RH=RH3; retTbl.T=T3
	
	return retTbl
end


local tbl=Compute{T1=14, T2=32, P=100, V1=50, V2=20, RH1=100, RH2=60}

print("Volumetric flow rate of mixture (m3/kg da):"..tostring(tbl.V))
print("Relative humidity of mixture (%):"..tostring(tbl.RH))
print("Temperature of mixture (C):"..tostring(tbl.T))