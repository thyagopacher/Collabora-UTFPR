<%@ include file="/Gerente/headGerente.jsp" %> 

   
        <div class="row">
          <div class="col-md-12">
            <div class="page-header">
              <h4>ADMINISTRAÇÃO GERAL&nbsp;
                <br>
                <br>
                <font color="#777777">
                  <span style="font-size: 15.3999996185303px; line-height: 23.3999996185303px;">Nesta área estão as opções para gerenciamento geral.&nbsp;</span>
                </font>
              </h4>
            </div>
          </div>
        </div>
   
          <div class="row">
            <div class="col-md-12">
              <h5 class="text-center">Opções de Gerenciamento:
                <br>
              </h5>
              <p class="text-center lead"></p>
            </div>
          </div>
          <div class="row">
          	<div class="col-md-12 col-sm-12">
             <div class="col-md-3 col-sm-3">
               <a href="<s:url namespace = '/Gerente' action= 'manterAluno'/>" class="thumbnail">
               	<img src="img\GeAluno.png" ></a>
             </div>
             <div class="col-md-3 col-sm-3">
               <a href="<s:url namespace = '/Gerente' action= 'manterDisciplina'/>" class="thumbnail">
               <img src="img\GeDisci.png"></a>
             </div>
             <div class="col-md-3 col-sm-3">
               <a href="<s:url namespace = '/Gerente' action= 'manterTurma'/>" class="thumbnail">
               <img src="img\GeTurma.png" ></a>
             </div>
             <div class="col-md-3 col-sm-3">
               <a href="<s:url namespace = '/Gerente' action= 'manterProfessor'/>" class="thumbnail">
               <img src="img\GeProf.png" 	></a>
             </div>
            	</div>
          </div>
        
  
