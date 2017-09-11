<f:with bean="${publicacao}">
    <f:field property="titulo" />
    <f:field property="conteudo" >
        <ckeditor:editor name="conteudo" height="400px" width="80%">
            ${publicacao.conteudo}
        </ckeditor:editor>
    </f:field>
</f:with>